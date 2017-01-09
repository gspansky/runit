package szczepanski.gerard.runit.settings.service.spliterator.impl;

import java.util.Arrays;
import java.util.List;

import szczepanski.gerard.runit.settings.service.spliterator.PropertySpliterator;

public class StringPropertySpliterator implements PropertySpliterator<String>{

	@Override
	public List<String> fromPropertyString(String propertyString) {
		String[] splittedStrings = propertyString.split(DELIMETER);
		return Arrays.asList(splittedStrings);
	}

}