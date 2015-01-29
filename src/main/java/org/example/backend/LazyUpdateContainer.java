package org.example.backend;

import com.vaadin.cdi.ViewScoped;
import com.vaadin.data.Item;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.vaadin.addons.lazyquerycontainer.LazyQueryContainer;
import org.vaadin.addons.lazyquerycontainer.Query;
import org.vaadin.addons.lazyquerycontainer.QueryDefinition;
import org.vaadin.addons.lazyquerycontainer.QueryFactory;
import org.vaadin.viritin.DynaBeanItem;

/**
 *
 * @author Matti Tahvonen
 */
@ViewScoped
public class LazyUpdateContainer extends LazyQueryContainer {

    private static final int BATCH_SIZE = 40;

    @Inject
    public LazyUpdateContainer(GPSRouteService service) {

        super(new QueryFactory() {

            public Query constructQuery(
                    QueryDefinition qd) {
                Query query = new Query() {

                    @Override
                    public int size() {
                        return (int) service.getEntityCount();
                    }

                    @Override
                    public List<Item> loadItems(int startIndex, int count) {
                        return service.find(startIndex, count).stream()
                                .map(u -> new DynaBeanItem(u))
                                .collect(Collectors.toList());
                    }

                    @Override
                    public void saveItems(List<Item> list, List<Item> list1,
                            List<Item> list2) {
                        throw new UnsupportedOperationException(
                                "Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public boolean deleteAllItems() {
                        throw new UnsupportedOperationException(
                                "Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public Item constructItem() {
                        throw new UnsupportedOperationException(
                                "Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                };

                return query;
            }
        }, "id", BATCH_SIZE, false);
        
        addContainerProperty("id", Long.class, null);
        addContainerProperty("timestamp", Date.class, null);
        addContainerProperty("lon", Double.class, null);
        addContainerProperty("lat", Double.class, null);
        addContainerProperty("speed", Double.class, null);
    }

}
