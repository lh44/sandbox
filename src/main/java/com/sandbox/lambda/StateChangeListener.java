package com.sandbox.lambda;

import javax.swing.plaf.nimbus.State;

public interface StateChangeListener {

    void onStateChange(State oldState, State newState);

}
