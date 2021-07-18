package com.sandbox.collections;

import java.util.Date;

public class Model implements Comparable<Model> {

        private String name;

        private Date createdOn;

        private long id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(Date createdOn) {
            this.createdOn = createdOn;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        @Override
        public int compareTo(Model mdl) {
            return this.getCreatedOn().compareTo(mdl.getCreatedOn());
        }
    }