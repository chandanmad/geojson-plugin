package com.chandan.osmosis.plugin.geojson.converter;

import com.chandan.geojson.model.*;
import com.chandan.osmosis.plugin.geojson.cache.FeatureLinestringCache;
import com.chandan.osmosis.plugin.geojson.cache.FeaturePointCache;
import com.chandan.osmosis.plugin.geojson.cache.FeaturePolygonCache;
import com.chandan.osmosis.plugin.geojson.common.Utils;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.domain.v0_6.WayNode;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chandan on 5/4/16.
 */
public class OsmWayToFeaturePolygonConverter implements OsmToFeatureConverter<Way, Polygon> {

	private final FeaturePointCache pointCache;

	private final FeaturePolygonCache polygonCache;

	public OsmWayToFeaturePolygonConverter(FeaturePointCache pointCache, FeaturePolygonCache polygonCache) {
		this.pointCache = pointCache;
		this.polygonCache = polygonCache;
	}

	@Override
	public Feature<Polygon> convert(Way t) {

		if (t == null && (t.getWayNodes() == null || t.getWayNodes().size() <= 1)) {
			return null;
		}
		if (!Utils.isPolygon(t)) {
			return null;
		}

		Feature.FeatureBuilder<LineString> featureBuilder = Feature.builder();

		List<Coordinate> coordinates = new ArrayList<Coordinate>(t.getWayNodes().size());
		for (WayNode node : t.getWayNodes()) {
			Feature<Point> point = pointCache.get(node.getNodeId());
			if (point == null) {
				throw new IllegalStateException(MessageFormat.format(
						"Node id : {0} not present in cache, for way id : {1}",
						new Object[] { node.getNodeId(), t.getId() }));
			}
			coordinates.add(point.getGeometry().getCoordinates());
		}
		featureBuilder.geometry(new LineString(coordinates));
		featureBuilder.id(t.getId());
		Utils.setPropertiesForFeature(t, featureBuilder);
		Feature<LineString> feature = featureBuilder.build();

		return null;
	}
}
