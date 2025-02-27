package com.dlsc.jfxcentral2.utils;

import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.Dependency;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral2.model.IkonData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.IkonProvider;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;

public class IkonliPackUtil {

    private static final IkonliPackUtil instance = new IkonliPackUtil();
    private final Map<Ikon, IkonData> dataMap = new HashMap<>();
    private final Map<String, IkonData> nameMap = new HashMap<>();
    private final Set<IkonData> ikonDataSet = new TreeSet<>();

    private IkonliPackUtil() {
        if (null != IkonProvider.class.getModule().getLayer()) {
            for (IkonProvider provider : ServiceLoader.load(IkonProvider.class.getModule().getLayer(), IkonProvider.class)) {
                ikonDataSet.add(IkonData.of(provider));
            }
        } else {
            for (IkonProvider provider : ServiceLoader.load(IkonProvider.class)) {
                ikonDataSet.add(IkonData.of(provider));
            }
        }
        List<IkonliPack> ikonliPacks = DataRepository2.getInstance().getIkonliPacks();

        ikonDataSet.forEach(data -> {
            for (IkonliPack pack : ikonliPacks) {
                if (pack.getName().equals(data.getName())) {
                    data.setIkonliPack(pack);
                    break;
                }
            }
            IkonProvider ikonProvider = data.getIkonProvider();
            Class ikonProviderClass = ikonProvider.getIkon();
            nameMap.put(ikonProviderClass.getSimpleName(), data);
            EnumSet enumSet = EnumSet.allOf(ikonProviderClass);
            enumSet.forEach(icon -> dataMap.put((Ikon) icon, data));
        });
    }

    public static IkonliPackUtil getInstance() {
        return instance;
    }

    public IkonData getIkonData(Ikon ikon) {
        return dataMap.get(ikon);
    }

    public Set<IkonData> getIkonDataSet() {
        return ikonDataSet;
    }

    public Map<Ikon, IkonData> getDataMap() {
        return dataMap;
    }

    public IkonData getIkonData(String simpleName) {
        return nameMap.get(simpleName);
    }

    public ObservableList<Ikon> getIkonList(IkonliPack iconPack) {
        IkonData ikonData = getIkonData(iconPack.getName());
        IkonProvider ikonProvider = ikonData.getIkonProvider();
        EnumSet enumSet = EnumSet.allOf(ikonProvider.getIkon());
        ObservableList<Ikon> list = FXCollections.observableArrayList(enumSet);
        for (Ikon ikon : list) {
            IkonData tempData = getIkonData(ikon);
            if (tempData != null && tempData.getIkonliPack() == null) {
                tempData.setIkonliPack(iconPack);
            }
        }
        return list;
    }
    public String getMavenDependency(Ikon ikon) {
        IkonliPack ikonliPack = getIkonData(ikon).getIkonliPack();
        Dependency dependency = ikonliPack.getInstalling().getMaven().getDependency();
        String line = System.lineSeparator();
        return "<dependency>" + line +
                "    <groupId>" + dependency.getGroupId() + "</groupId>" + line +
                "    <artifactId>" + dependency.getArtifactId() + "</artifactId>" + line +
                "    <version>" + dependency.getVersion() + "</version>" + line +
                "</dependency>";
    }

    public String getGradleDependency(Ikon ikon) {
        return getIkonData(ikon).getIkonliPack().getInstalling().getGradle();
    }
}
