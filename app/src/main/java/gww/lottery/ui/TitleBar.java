package gww.lottery.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gww.lottery.R;


/**
 * Created by 高文文 on 2016/11/14.
 */

public class TitleBar extends LinearLayout implements View.OnClickListener{

    private float TAG_SIZE;
    private float OP_SIZE;
    private float ALL_SIZE;

    private int TAG_COLOR;
    private int OP_COLOR;
    private int IMG_BAG;
    private int LEFT_BAG;
    private int ALL_BAG;

    private String TAG_STRING;
    private String OP_STRING;

    private boolean IMG_VISIBLE;
    private boolean OP_VISIBLE;
    private boolean TV_VISIBLE;
    private boolean LINE_VISIBLE;

    public TitleBar(Context context) {
        super(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private RelativeLayout mContainer;
    private LinearLayout mLltLeft;
    private ImageView mIvBack;
    private TextView mTvBack;
    private TextView mTvTag;
    private TextView mTvOperation;

    private View mLine;

    private void initView(Context context, AttributeSet attrs) {
        View view = null;
        view = LayoutInflater.from(getContext()).inflate(R.layout.title_bar, this);

       mContainer = (RelativeLayout) view.findViewById(R.id.container);
       mLltLeft = (LinearLayout) view.findViewById(R.id.llt_left);
       mIvBack = (ImageView) view.findViewById(R.id.iv_back);
       mTvBack = (TextView) view.findViewById(R.id.tv_back);
       mTvTag  = (TextView) view.findViewById(R.id.tv_tag);
       mTvOperation = (TextView) view.findViewById(R.id.tv_operation);
       mLine = (View) view.findViewById(R.id.line);

       mLltLeft.setOnClickListener(this);
       mTvOperation.setOnClickListener(this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);

        TAG_SIZE = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tagSize, 0);
        OP_SIZE = typedArray.getDimensionPixelSize(R.styleable.TitleBar_opSize, 0);
        ALL_SIZE = typedArray.getDimensionPixelSize(R.styleable.TitleBar_allSize, 0);

        TAG_COLOR = typedArray.getColor(R.styleable.TitleBar_tagColor, 0);
        OP_COLOR = typedArray.getColor(R.styleable.TitleBar_tagColor, 0);
        IMG_BAG = typedArray.getResourceId(R.styleable.TitleBar_imgBag, 0);
        LEFT_BAG = typedArray.getResourceId(R.styleable.TitleBar_leftBag, 0);
        ALL_BAG = typedArray.getResourceId(R.styleable.TitleBar_allBag, 0);


        TAG_STRING = typedArray.getString(R.styleable.TitleBar_tagStr);
        OP_STRING = typedArray.getString(R.styleable.TitleBar_opStr);

        IMG_VISIBLE = typedArray.getBoolean(R.styleable.TitleBar_imgVisible, false);
        OP_VISIBLE = typedArray.getBoolean(R.styleable.TitleBar_opVisible, false);
        TV_VISIBLE = typedArray.getBoolean(R.styleable.TitleBar_tvVisible, false);
        LINE_VISIBLE = typedArray.getBoolean(R.styleable.TitleBar_lineVisible, false);

        if (IMG_VISIBLE != false) {
            mLltLeft.setVisibility(View.VISIBLE);
        } else {
            mLltLeft.setVisibility(View.INVISIBLE);
        }

        if (OP_VISIBLE != false) {
            mTvOperation.setVisibility(View.VISIBLE);
        } else {
            mTvOperation.setVisibility(View.INVISIBLE);
        }

        if (TV_VISIBLE != false) {
            mTvBack.setVisibility(View.VISIBLE);
        } else {
            mTvBack.setVisibility(View.INVISIBLE);
        }
        if (LINE_VISIBLE != false) {
            mLine.setVisibility(View.VISIBLE);
        } else {
            mLine.setVisibility(View.INVISIBLE);
        }


        if (TAG_SIZE != 0)
            mTvTag.setTextSize(TAG_SIZE);
        if (OP_SIZE != 0)
            mTvOperation.setTextSize(OP_SIZE);
        if (ALL_SIZE != 0) {
            mTvTag.setTextSize(ALL_SIZE);
            mTvOperation.setTextSize(ALL_SIZE);
        }

        if (TAG_COLOR != 0)
            mTvTag.setTextColor(TAG_COLOR);
        if (OP_COLOR != 0)
            mTvTag.setTextColor(OP_COLOR);
        if (IMG_BAG != 0)
            mIvBack.setImageResource(IMG_BAG);
        if (LEFT_BAG != 0)
            mLltLeft.setBackgroundResource(LEFT_BAG);
        if (ALL_BAG != 0)
            mContainer.setBackgroundResource(ALL_BAG);

        TAG_STRING = null == TAG_STRING ? "" : TAG_STRING;
        mTvTag.setText(TAG_STRING);
        OP_STRING = null == OP_STRING ? "" : OP_STRING;
        mTvOperation.setText(OP_STRING);
        typedArray.recycle();
    }

    /**
     * 通过接口实现解耦
     */
    public interface OnEventClickListener {
        void onBack(View view);
        void onOperation(View view);
    }

    private OnEventClickListener listener;

    public void setListener(OnEventClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llt_left:
                listener.onBack(v);
                break;
            case R.id.tv_operation:
                listener.onOperation(v);
                break;
        }
    }

    public void setTag(String text) {
        mTvTag.setText(text);
    }
    public void setOperation(String text) {
        mTvOperation.setText(text);
    }

    public void setTagColor(int color) {
        mTvTag.setTextColor(color);
    }
    public void setOperationColor(int color) {
        mTvOperation.setTextColor(color);
    }
}
