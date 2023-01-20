package org.nbu.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AttributeMerger {

    public static <T> T mergeAttribute(T originalAttribute, T deltaAttribute) {
        if (deltaAttribute != null) {
            return deltaAttribute;
        }

        if (originalAttribute != null) {
            return originalAttribute;
        }

        return null;
    }

}
