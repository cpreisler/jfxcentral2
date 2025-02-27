package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Documentation;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class DocumentationFilterView extends SimpleModelObjectSearchFilterView<Documentation> {
    private static List<FilterItem<Documentation>> typeFilterItems;

    public DocumentationFilterView() {
        getStyleClass().addAll("tools-filter-view","doc-filter-view");

        setSearchPromptText("Search for a Documentation");

        setOnSearch(text -> tool -> StringUtils.isBlank(text)
                || StringUtils.containsIgnoreCase(tool.getName(), text)
                || StringUtils.containsIgnoreCase(tool.getDescription(), text)
        );

        if (typeFilterItems == null) {
            typeFilterItems = getVideoFilterItems(Documentation::getTags, createFilterPredicate(Documentation::getTags));
        }
        getFilterGroups().setAll(List.of(
                new FilterGroup<>("TYPE", typeFilterItems)
        ));
    }

    private BiPredicate<Documentation, String> createFilterPredicate(Function<Documentation, String> attrGetter) {
        return (doc, item) -> {
            String attribute = attrGetter.apply(doc);
            return attribute != null && StringUtils.containsIgnoreCase(attribute, item);
        };
    }
    private List<FilterItem<Documentation>> getVideoFilterItems(
            Function<Documentation, String> attrGetter,
            BiPredicate<Documentation, String> predicate) {
        List<Documentation> appList = DataRepository2.getInstance().getDocumentation();

        ArrayList<FilterItem<Documentation>> filterItems = new ArrayList<>(appList.stream()
                .flatMap(app -> Optional.ofNullable(attrGetter.apply(app)).stream()
                        .flatMap(attr -> Arrays.stream(attr.split(","))))
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .sorted()
                .distinct()
                .map(item -> new FilterItem<Documentation>(item, it -> predicate.test(it, item)))
                .toList());
        filterItems.add(0, new FilterItem<>("ALL", item -> true, true));

        return filterItems;
    }
}

