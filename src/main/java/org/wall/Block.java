package org.wall;

import org.visitor.ItemElement;

public interface Block extends ItemElement {

    String getColor();

    String getMaterial();
}
