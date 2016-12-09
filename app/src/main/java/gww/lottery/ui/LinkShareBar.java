package gww.lottery.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import gww.lottery.R;

/**
 * Created by 高文文 on 2016/12/9.
 */

public class LinkShareBar extends LinearLayout {
    public LinkShareBar(Context context) {
        super(context);
    }

    public LinkShareBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LinkShareBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.link_share_bar, this);
        RelativeLayout rlShare = (RelativeLayout) view.findViewById(R.id.rl_share);
        RelativeLayout rlComment = (RelativeLayout) view.findViewById(R.id.rl_comment);
        RelativeLayout rlLink = (RelativeLayout) view.findViewById(R.id.rl_link);
        RelativeLayout rlType = (RelativeLayout) view.findViewById(R.id.rl_tag);
        rlShare.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) {
                    listener.onShareTagClick(view);
                }
            }
        });
        rlComment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) {
                    listener.onCommentTagClick(view);
                }
            }
        });

       rlLink.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {
               if(listener != null) {
                   listener.onLinkTagClick(view);
               }
           }
       });

        rlType.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) {
                    listener.onTypeTagClick(view);
                }
            }
        });
    }
    private OnTagClickListener listener;

    public OnTagClickListener getListener() {
        return listener;
    }

    public void setListener(OnTagClickListener listener) {
        this.listener = listener;
    }

    public interface OnTagClickListener {
        void onShareTagClick(View view);
        void onCommentTagClick(View view);
        void onLinkTagClick(View view);
        void onTypeTagClick(View view);
    }
}
