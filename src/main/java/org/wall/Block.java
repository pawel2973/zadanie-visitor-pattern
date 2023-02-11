package org.wall;

import org.visitor.Visitable;

public interface Block extends Visitable {

    String getColor();

    String getMaterial();
}
