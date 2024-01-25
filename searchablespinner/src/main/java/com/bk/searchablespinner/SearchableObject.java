package com.bk.searchablespinner;

import java.io.Serializable;

public interface SearchableObject extends Serializable {
    SearchableObject onSearchableItemClicked();

    String toSearchableString();
}
