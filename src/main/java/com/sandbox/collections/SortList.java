package com.sandbox.collections;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SortList {

    public List<Model> getModels() {
        List<Model> models = new ArrayList();
        for (int i=0; i<10; i++) {
            Model model = new Model();
            model.setId(i);
            Long rand = new Random().nextLong();
            LocalDate localDate = LocalDate.now().plusDays(rand.shortValue());
            Date date = Date.from(localDate.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
            model.setCreatedOn(date);
            models.add(model);
        }
        return models;
    }

    public boolean isSorted(List<Model> models) {
        for (int i=0; i<models.size() - 1; i++) {
            if (models.get(i).getCreatedOn().compareTo(models.get(i+1).getCreatedOn()) != 1) {
                return false;
            }
        }
        return true;
    }

    //Test
    public void testIsSorted() {
        List<Model> models = new ArrayList();
        for (int i=0; i<10; i++) {
            Model model = new Model();
            model.setId(i);
            LocalDate localDate = LocalDate.now().plusDays(i);
            Date date = Date.from(localDate.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
            model.setCreatedOn(date);
            models.add(model);
        }
        if (isSorted(models) == false) {
           // throw new RuntimeException();
        }
    }

}
