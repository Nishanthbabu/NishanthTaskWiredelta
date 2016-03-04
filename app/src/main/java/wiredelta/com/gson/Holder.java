package wiredelta.com.gson;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Nishanth on 3/3/2016.
 */
public class Holder extends RecyclerView.ViewHolder{
    public TextView id,name,owner,startdate,descri,dept;



    public Holder(View itemView) {
        super(itemView);
        id=(TextView) itemView.findViewById(R.id.textViewID);
        name=(TextView) itemView.findViewById(R.id.textViewName);
        owner=(TextView) itemView.findViewById(R.id.textViewOwner);
        startdate=(TextView) itemView.findViewById(R.id.textViewDate);
        descri=(TextView) itemView.findViewById(R.id.textViewDesc);
        dept=(TextView) itemView.findViewById(R.id.textViewDepartments);



    }
}
