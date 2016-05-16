package com.crux;

import com.activeandroid.serializer.TypeSerializer;
import com.crux.util.StringUtils;

/**
 * @author gauravarora
 * @since 01/05/16.
 */
public class Configuration {

    private static Configuration sDefaultConfiguration = getDefaultConfiguration();

    private String mDatabaseName;
    private int mDatabaseVersion;
    private String mDefaultFont;
    private Class[] mTableClasses;
    private Class<? extends TypeSerializer>[] mTypeSerializers;

    public static Configuration getDefaultConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setDatabaseName("crux");
        configuration.setDatabaseVersion(1);
        return configuration;
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

}
