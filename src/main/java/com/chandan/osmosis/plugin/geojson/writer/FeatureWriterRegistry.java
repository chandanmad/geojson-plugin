package com.chandan.osmosis.plugin.geojson.writer;

import org.openstreetmap.osmosis.core.OsmosisRuntimeException;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chandan on 12/06/16.
 */
public class FeatureWriterRegistry {

    private Map<String, FeatureWriter> registry = new HashMap<>();

    private FeatureWriterRegistry() {
    }

    private static FeatureWriterRegistry instance;

    static {
        instance = new FeatureWriterRegistry();
    }

    public static FeatureWriterRegistry getInstance() {
        return instance;
    }

    public void registerWriter(String name, FeatureWriter writer) {
        registry.put(name, writer);
    }

    public FeatureWriter getFileWriter(String name) {
        FeatureWriter featureWriter = registry.get(name);
        if (featureWriter == null) {
            throw new OsmosisRuntimeException("geojson-writer " + name + " is invalid");
        }
        return featureWriter;
    }
}
