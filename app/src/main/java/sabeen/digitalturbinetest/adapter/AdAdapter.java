package sabeen.digitalturbinetest.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import sabeen.digitalturbinetest.R;
import sabeen.digitalturbinetest.model.Ad;

/**
 * Created by sabeen on 8/12/16.
 * RecylerView Adapter for displaying Ad list in recycler view
 */

public class AdAdapter extends RecyclerView.Adapter<AdAdapter.AdViewHolder> {
    private List<Ad> adList;

    public AdAdapter(List<Ad> adList) {
        this.adList = adList;
    }


    @Override
    public AdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ad_items, parent, false);
        return new AdViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdViewHolder holder, int position) {
        Ad ad = adList.get(position);
        holder.productNameTV.setText(ad.getProductName());
        holder.productDescTV.setText(ad.getProductDescription());
        holder.productCategoryTV.setText(ad.getCategoryName());
        holder.productRatingTV.setText(ad.getRating());
        //For Loading product thumbnail
        Picasso.with(holder.proudctThumbnailIV.getContext())
                .load(ad.getProductThumbnail())
                .resize(150, 150)
                .centerCrop()
                .placeholder(android.R.drawable.sym_def_app_icon)
                .into(holder.proudctThumbnailIV);
    }

    @Override
    public int getItemCount() {
        return adList.size();
    }

    public class AdViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView productNameTV, productDescTV, productCategoryTV, productRatingTV;
        public ImageView proudctThumbnailIV;

        public AdViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            productNameTV = (TextView) itemView.findViewById(R.id.product_name_TV);
            productDescTV = (TextView) itemView.findViewById(R.id.product_desc_TV);
            productCategoryTV = (TextView) itemView.findViewById(R.id.product_category_TV);
            productRatingTV = (TextView) itemView.findViewById(R.id.product_rating_TV);
            proudctThumbnailIV = (ImageView) itemView.findViewById(R.id.product_thumbnail_IV);
        }

        /**
         * On Click listener For Ad list
         * Fetches app Id of the Ad and takes user to play store if clicked
         * @param v item clicked
         */
        @Override
        public void onClick(View v) {
            Ad contributor = adList.get(getLayoutPosition());
            String appPackageName = contributor.getAppId();
            try {
                v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }
    }
}
