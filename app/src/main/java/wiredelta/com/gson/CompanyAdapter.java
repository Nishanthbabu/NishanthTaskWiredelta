package wiredelta.com.gson;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Nishanth on 3/3/2016.
 */
public class CompanyAdapter extends RecyclerView.Adapter<Holder> {

    Context context = null;
    ArrayList<CompanyModel> models = null;
    private final SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

    public CompanyAdapter(Context context, ArrayList<CompanyModel> models)
    {
        this.context = context;
        this.models = models;
    }



    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_single_contact,parent,false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.id.setText(models.get(position).getCompanyID());
        holder.name.setText(models.get(position).getCompanyName());
        holder.owner.setText(models.get(position).getCompanyOwner());
        holder.startdate.setText(sdf.format(models.get(position).getCompanyStartDate()));
        holder.descri.setText(models.get(position).getCompanyDescription());
        holder.dept.setText(models.get(position).getCompanyDepartments());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
