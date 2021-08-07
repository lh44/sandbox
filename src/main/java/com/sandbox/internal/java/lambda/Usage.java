package com.sandbox.internal.java.lambda;

import javax.swing.plaf.nimbus.State;

public class Usage {

    public void usage() {
        StateOwner stateOwner = new StateOwner();
        stateOwner.addStateListener(new StateChangeListener() {
            @Override
            public void onStateChange(State oldState, State newState) {
                //Do Something
            }
        });
    }
}
