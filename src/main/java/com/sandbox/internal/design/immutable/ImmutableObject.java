package com.sandbox.internal.design.immutable;

public final class ImmutableObject {

    private final int content;

    public ImmutableObject(int content) {
        this.content = content;
    }

    public int getContent() {
        return content;
    }
}
