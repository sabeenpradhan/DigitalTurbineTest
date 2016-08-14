package sabeen.digitalturbinetest.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Model For Ads
 * Annotated @root for using xml parser of retrofit
 * Created by sabeen on 8/12/16.
 */

@Root(name = "ads",strict = false)
public class Ads {
    @ElementList(name = "ad",inline = true)
    private List<Ad> adList;

    public List<Ad> getAdList() {
        return adList;
    }

}
