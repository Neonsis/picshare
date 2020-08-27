package org.neonsis.picshare.model;

import org.neonsis.picshare.exception.ValidationException;

public enum SortMode {

    POPULAR_PHOTO,

    POPULAR_AUTHOR;

    public static SortMode of(String name) {
        for (SortMode value : SortMode.values()) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        throw new ValidationException("Undefined sort mode: " + name.toUpperCase());
    }
}
