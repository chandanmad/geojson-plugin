package com.chandan.osmosis.plugin.geojson.processor;

import com.chandan.geojson.model.Feature;
import com.chandan.geojson.model.Point;
import com.chandan.geojson.model.Polygon;
import com.chandan.osmosis.plugin.geojson.cache.FeaturePointCache;
import com.chandan.osmosis.plugin.geojson.converter.OsmNodeToFeaturePointConverter;
import com.chandan.osmosis.plugin.geojson.writer.FeatureWriter;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;

import java.util.List;

/**
 * Created by chandan on 5/5/16.
 */
public class OsmNodeProcessor extends OsmEntityProcessor<Node> {

	private final FeaturePointCache featurePointCache;

	private final OsmNodeToFeaturePointConverter osmNodeToFeaturePointConverter;

	public OsmNodeProcessor(FeaturePointCache featurePointCache) {
		this.featurePointCache = featurePointCache;
		this.osmNodeToFeaturePointConverter = new OsmNodeToFeaturePointConverter(featurePointCache);
	}

	@Override
	public void process(Node node) {
		Feature<Point> pointFeature = osmNodeToFeaturePointConverter.convert(node);
		featurePointCache.put(node.getId(), pointFeature);
	}
}
