package fit5120.seniorparking;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by liyunhong on 28/3/17.
 */

public class SpinnerActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
       /** Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                Toast.LENGTH_SHORT).show();**/
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       /** Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();**/
    }
}
