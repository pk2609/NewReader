package com.appexample.newsreader;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.myviewholder>{


    private List<Main2Activity> myNews;
    private Context context;

    public class myviewholder extends RecyclerView.ViewHolder {

        LinearLayout layout1;
        public TextView author, title, description, published;
        public ImageView img;


        public myviewholder(View itemView) {
            super(itemView);

            author = (TextView) itemView.findViewById(R.id.author);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.desc);
            published = (TextView) itemView.findViewById(R.id.published);
            layout1 = (LinearLayout) itemView.findViewById(R.id.layout1);
            img= (ImageView) itemView.findViewById(R.id.img);

        }

    }

    public Adapter(Context context,List<Main2Activity> myNews) {
        this.myNews = myNews;
        this.context=context;
    }
    @Override
    public myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new myviewholder(itemView);
    }

    @Override
    public void onBindViewHolder(myviewholder holder, int position) {
        final Main2Activity News = myNews.get(position);
        holder.author.setText(News.getAuthor());
        holder.title.setText(News.getTitle());
        holder.published.setText(News.getPublished());
        holder.description.setText(News.getDescription());
        if(!News.getPublished().contentEquals("null")){
            holder.published.setText(News.getPublished());
        }else {
            holder.published.setText("");
        }
        if(!News.getAuthor().contentEquals("null")){
            holder.author.setText(News.getAuthor());
        }else {
            holder.author.setText("");
        }
        Picasso.with(context).load(News.getImgurl()).into(holder.img);
        holder.layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(News.getUrl()));
                context.startActivity(i);
            }
        });
        //Glide.with(context).load("").placeholder(heroine.img_id).into(holder.img);
       // holder.layout1.setOnClickListener(new View.OnClickListener() {
           // @Override
            //public void onClick(View view) {
               // Toast.makeText(context, Main2Activity.getTitle(), Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(context, Main2Activity.class);
               // intent.putExtra("Name",heroine.getName());
                //intent.putExtra("Image",heroine.img_id);
                //intent.putExtra("Description",heroine.getDescription());
                //context.startActivity(intent);
           // }
        //});
              /*  try{
                 holder.img.setBackgroundResource(movie.img_id);
                }catch (OutOfMemoryError e){
                    holder.img.setBackgroundResource(movie.img_id);
                }*/
    }

    @Override
    public int getItemCount() {
        return myNews.size();
    }

}
