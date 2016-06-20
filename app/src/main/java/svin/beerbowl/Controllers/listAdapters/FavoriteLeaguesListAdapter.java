package svin.beerbowl.Controllers.listAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import svin.beerbowl.R;
import svin.beerbowl.models.League;
import svin.beerbowl.models.Player;

/**
 * Created by AbstraktenPC on 20-06-2016.
 */
public class FavoriteLeaguesListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<League> leagueList;

    public FavoriteLeaguesListAdapter(Context context, List<League> leagues, Player player){

        mInflater = LayoutInflater.from(context);
        leagueList = leagues;
    }

    @Override
    public int getCount()
    {
        return leagueList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return leagueList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view;
        ViewHolder holder;
        if(convertView == null)
        {
            view = mInflater.inflate(R.layout.league_list_item, parent, false);
            holder = new ViewHolder();
            holder.leagueName = (TextView)view.findViewById(R.id.leagueName);
            holder.leagueRating = (TextView)view.findViewById(R.id.leagueRating);
            holder.submitMatch = (Button) view.findViewById(R.id.submitMatchButton);
            view.setTag(holder);
        } else
        {
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }

        League league = leagueList.get(position);

        holder.leagueName.setText(league.getName());
        //TODO get personal rating
        holder.leagueRating.setText(league.getParticipants().get(0).getPlayerRating());
        holder.submitMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO make button open new match window
            }
        });

        return view;
    }

    private class ViewHolder
    {
        public TextView leagueName;
        public TextView leagueRating;
        public Button submitMatch;
    }

}
