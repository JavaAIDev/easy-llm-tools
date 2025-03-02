
package com.javaaidev.easyllmtools.tools.mytool.model;

import java.util.HashMap;
import java.util.Map;

public class GetWeatherConfiguration {

    /**
     * Unit of temperature
     * 
     */
    private GetWeatherConfiguration.TemperatureUnit temperatureUnit;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetWeatherConfiguration() {
    }

    /**
     * 
     * @param temperatureUnit
     *     Unit of temperature.
     */
    public GetWeatherConfiguration(GetWeatherConfiguration.TemperatureUnit temperatureUnit) {
        super();
        this.temperatureUnit = temperatureUnit;
    }

    public static GetWeatherConfiguration.GetWeatherConfigurationBuilderBase builder() {
        return new GetWeatherConfiguration.GetWeatherConfigurationBuilder();
    }

    /**
     * Unit of temperature
     * 
     */
    public GetWeatherConfiguration.TemperatureUnit getTemperatureUnit() {
        return temperatureUnit;
    }

    /**
     * Unit of temperature
     * 
     */
    public void setTemperatureUnit(GetWeatherConfiguration.TemperatureUnit temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(GetWeatherConfiguration.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("temperatureUnit");
        sb.append('=');
        sb.append(((this.temperatureUnit == null)?"<null>":this.temperatureUnit));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.temperatureUnit == null)? 0 :this.temperatureUnit.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GetWeatherConfiguration) == false) {
            return false;
        }
        GetWeatherConfiguration rhs = ((GetWeatherConfiguration) other);
        return ((this.temperatureUnit == rhs.temperatureUnit)||((this.temperatureUnit!= null)&&this.temperatureUnit.equals(rhs.temperatureUnit)));
    }

    public static class GetWeatherConfigurationBuilder
        extends GetWeatherConfiguration.GetWeatherConfigurationBuilderBase<GetWeatherConfiguration>
    {


        public GetWeatherConfigurationBuilder() {
            super();
        }

        public GetWeatherConfigurationBuilder(GetWeatherConfiguration.TemperatureUnit temperatureUnit) {
            super(temperatureUnit);
        }

    }

    public static abstract class GetWeatherConfigurationBuilderBase<T extends GetWeatherConfiguration >{

        protected T instance;

        @SuppressWarnings("unchecked")
        public GetWeatherConfigurationBuilderBase() {
            // Skip initialization when called from subclass
            if (this.getClass().equals(GetWeatherConfiguration.GetWeatherConfigurationBuilder.class)) {
                this.instance = ((T) new GetWeatherConfiguration());
            }
        }

        @SuppressWarnings("unchecked")
        public GetWeatherConfigurationBuilderBase(GetWeatherConfiguration.TemperatureUnit temperatureUnit) {
            // Skip initialization when called from subclass
            if (this.getClass().equals(GetWeatherConfiguration.GetWeatherConfigurationBuilder.class)) {
                this.instance = ((T) new GetWeatherConfiguration(temperatureUnit));
            }
        }

        public T build() {
            T result;
            result = this.instance;
            this.instance = null;
            return result;
        }

        public GetWeatherConfiguration.GetWeatherConfigurationBuilderBase withTemperatureUnit(GetWeatherConfiguration.TemperatureUnit temperatureUnit) {
            ((GetWeatherConfiguration) this.instance).temperatureUnit = temperatureUnit;
            return this;
        }

    }


    /**
     * Unit of temperature
     * 
     */
    public enum TemperatureUnit {

        C("C"),
        F("F");
        private final String value;
        private final static Map<String, GetWeatherConfiguration.TemperatureUnit> CONSTANTS = new HashMap<String, GetWeatherConfiguration.TemperatureUnit>();

        static {
            for (GetWeatherConfiguration.TemperatureUnit c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        TemperatureUnit(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static GetWeatherConfiguration.TemperatureUnit fromValue(String value) {
            GetWeatherConfiguration.TemperatureUnit constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
