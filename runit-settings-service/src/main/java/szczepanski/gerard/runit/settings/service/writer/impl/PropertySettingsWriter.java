package szczepanski.gerard.runit.settings.service.writer.impl;

import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import szczepanski.gerard.runit.common.exception.ExceptionCode;
import szczepanski.gerard.runit.common.exception.RunitRuntimeException;
import szczepanski.gerard.runit.settings.service.loader.Settings;
import szczepanski.gerard.runit.settings.service.loader.SettingsLoader;
import szczepanski.gerard.runit.settings.service.loader.impl.DefaultSettingsProvider;
import szczepanski.gerard.runit.settings.service.mapper.SettingsPropertiesMapper;
import szczepanski.gerard.runit.settings.service.writer.SettingsWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

@RequiredArgsConstructor
public class PropertySettingsWriter implements SettingsWriter {
    private static final Logger LOG = Logger.getLogger(PropertySettingsWriter.class);

    private final String propertiesPath;
    private final SettingsPropertiesMapper settingsPropertiesMapper;
    private final SettingsLoader settingsLoader;
    private final DefaultSettingsProvider defaultSettingsProvider;

    @Override
    public void updateSettings(Settings newSettings) {
        Properties properties = settingsPropertiesMapper.toProperties(newSettings);
        try {
            writeProperties(properties);
            settingsLoader.loadSettingsIntoMemory();
            LOG.debug("Settings has been updated");
        } catch (IOException | URISyntaxException e) {
            throw new RunitRuntimeException(ExceptionCode.R_011, e);
        }
    }

    @Override
    public void restoreDefaultSettings() {
        LOG.debug("Restore default settings");
        Settings defaultSettings = defaultSettingsProvider.loadDefaultSettings();
        updateSettings(defaultSettings);
    }

    private void writeProperties(Properties properties) throws IOException, URISyntaxException {
        FileOutputStream out = new FileOutputStream(propertiesPath);
        properties.store(out, "Update properties");
        out.close();
    }
}
