package com.crux;

import com.activeandroid.serializer.TypeSerializer;
import com.crux.util.StringUtils;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * @author gauravarora
 * @since 01/05/16.
 */
public class Configuration {

    private static Configuration sDefaultConfiguration = getDefaultConfiguration();

    //Database
    private boolean mDatabaseEnabled;
    private String mDatabaseName;
    private int mDatabaseVersion;
    private Class[] mTableClasses;
    private Class<? extends TypeSerializer>[] mTypeSerializers;
    private ImagePipelineConfig mFrescoPipelineConfig;

    //Others
    private String mDefaultFont;

    public static Configuration getDefaultConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setDatabaseName("crux");
        configuration.setDatabaseVersion(1);
        return configuration;
    }

    public boolean isDatabaseEnabled() {
        return mDatabaseEnabled;
    }

    public void setDatabaseEnabled(boolean databaseEnabled) {
        this.mDatabaseEnabled = databaseEnabled;
    }

    public String getDatabaseName() {
        if (StringUtils.isEmpty(mDatabaseName)) {
            return sDefaultConfiguration.getDatabaseName();
        }
        return mDatabaseName;
    }

    public Configuration setDatabaseName(String databaseName) {
        this.mDatabaseName = databaseName;
        return this;
    }

    public int getDatabaseVersion() {
        if (mDatabaseVersion == 0) {
            return sDefaultConfiguration.getDatabaseVersion();
        }
        return mDatabaseVersion;
    }

    public Configuration setDatabaseVersion(int databaseVersion) {
        this.mDatabaseVersion = databaseVersion;
        return this;
    }

    public String getDefaultFont() {
        return mDefaultFont;
    }

    public Configuration setDefaultFont(String defaultFont) {
        this.mDefaultFont = defaultFont;
        return this;
    }

    public Class[] getTableClasses() {
        return mTableClasses;
    }

    public Configuration setTableClasses(Class... classes) {
        this.mTableClasses = classes;
        return this;
    }

    public Class<? extends TypeSerializer>[] getTypeSerializers() {
        return mTypeSerializers;
    }

    public Configuration setTypeSerializers(Class<? extends TypeSerializer>... typeSerializers) {
        this.mTypeSerializers = typeSerializers;
        return this;
    }

    public void setFrescoPipelineConfig(ImagePipelineConfig config) {
        this.mFrescoPipelineConfig = config;
    }

    public ImagePipelineConfig getFrescoPipelineConfig() {
        return this.mFrescoPipelineConfig;
    }

}
