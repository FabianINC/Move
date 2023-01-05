package MainActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.archivo.move.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class B_RecyclerViewAdapter extends RecyclerView.Adapter<B_RecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Box> boxes;

   public B_RecyclerViewAdapter(Context context, ArrayList<Box> boxes){

     this.context = context;
     this.boxes = boxes;


   }


    @NonNull
    @Override
    public B_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reciclerview_element , parent , false);
       return new B_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull B_RecyclerViewAdapter.MyViewHolder holder, int position) {

       holder.tvLocation.setText(boxes.get(position).getLocation());
       holder.tvPrice.setText(boxes.get(position).getPrice());
       holder.imageView.setImageResource(boxes.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return boxes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        ImageView imageView;
        TextView tvLocation,tvPrice;

         public MyViewHolder(@NonNull View itemView){
             super(itemView);

             imageView = itemView.findViewById(R.id.recicler_img);
             tvLocation = itemView.findViewById(R.id.txt_Location);
             tvPrice = itemView.findViewById(R.id.txt_Price);


         }

    }

}
