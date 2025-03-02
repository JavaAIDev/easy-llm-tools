
package com.javaaidev.easyllmtools.tools.mytool.model;

import java.util.HashMap;
import java.util.Map;

public class GetWeatherReturnType {

    /**
     * Weather condition
     * 
     */
    private String condition;
    /**
     * Temperature
     * 
     */
    private Double temperature;
    /**
     * Unit of temperature
     * 
     */
    private GetWeatherReturnType.TemperatureUnit temperatureUnit;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetWeatherReturnType() {
    }

    /**
     * 
     * @param condition
     *     Weather condition.
     * @param temperatureUnit
     *     Unit of temperature.
     * @param temperature
     *     Temperature.
     */
    public GetWeatherReturnType(String condition, Double temperature, GetWeatherReturnType.TemperatureUnit temperatureUnit) {
        super();
        this.condition = condition;
        this.temperature = temperature;
        this.temperatureUnit = temperatureUnit;
    }

    public static GetWeatherReturnType.GetWeatherReturnTypeBuilderBase builder() {
        return new GetWeatherReturnType.GetWeatherReturnTypeBuilder();
    }

    /**
     * Weather condition
     * 
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Weather condition
     * 
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * Temperature
     * 
     */
    public Double getTemperature() {
        return temperature;
    }

    /**
     * Temperature
     * 
     */
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    /**
     * Unit of temperature
     * 
     */
    public GetWeatherReturnType.TemperatureUnit getTemperatureUnit() {
        return temperatureUnit;
    }

    /**
     * Unit of temperature
     * 
     */
    public void setTemperatureUnit(GetWeatherReturnType.TemperatureUnit temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(GetWeatherReturnType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("condition");
        sb.append('=');
        sb.append(((this.condition == null)?"<null>":this.condition));
        sb.append(',');
        sb.append("temperature");
        sb.append('=');
        sb.append(((this.temperature == null)?"<null>":this.temperature));
        sb.append(',');
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
        result = ((result* 31)+((this.temperature == null)? 0 :this.temperature.hashCode()));
        result = ((result* 31)+((this.temperatureUnit == null)? 0 :this.temperatureUnit.hashCode()));
        result = ((result* 31)+((this.condition == null)? 0 :this.condition.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GetWeatherReturnType) == false) {
            return false;
        }
        GetWeatherReturnType rhs = ((GetWeatherReturnType) other);
        return ((((this.temperature == rhs.temperature)||((this.temperature!= null)&&this.temperature.equals(rhs.temperature)))&&((this.temperatureUnit == rhs.temperatureUnit)||((this.temperatureUnit!= null)&&this.temperatureUnit.equals(rhs.temperatureUnit))))&&((this.condition == rhs.condition)||((this.condition!= null)&&this.condition.equals(rhs.condition))));
    }

    public static class GetWeatherReturnTypeBuilder
        extends GetWeatherReturnType.GetWeatherReturnTypeBuilderBase<GetWeatherReturnType>
    {


        public GetWeatherReturnTypeBuilder() {
            super();
        }

        public GetWeatherReturnTypeBuilder(String condition, Double temperature, GetWeatherReturnType.TemperatureUnit temperatureUnit) {
            super(condition, temperature, temperatureUnit);
        }

    }

    public static abstract class GetWeatherReturnTypeBuilderBase<T extends GetWeatherReturnType >{

        protected T instance;

        @SuppressWarnings("unchecked")
        public GetWeatherReturnTypeBuilderBase() {
            // Skip initialization when called from subclass
            if (this.getClass().equals(GetWeatherReturnType.GetWeatherReturnTypeBuilder.class)) {
                this.instance = ((T) new GetWeatherReturnType());
            }
        }

        @SuppressWarnings("unchecked")
        public GetWeatherReturnTypeBuilderBase(String condition, Double temperature, GetWeatherReturnType.TemperatureUnit temperatureUnit) {
            // Skip initialization when called from subclass
            if (this.getClass().equals(GetWeatherReturnType.GetWeatherReturnTypeBuilder.class)) {
                this.instance = ((T) new GetWeatherReturnType(condition, temperature, temperatureUnit));
            }
        }

        public T build() {
            T result;
            result = this.instance;
            this.instance = null;
            return result;
        }

        public GetWeatherReturnType.GetWeatherReturnTypeBuilderBase withCondition(String condition) {
            ((GetWeatherReturnType) this.instance).condition = condition;
            return this;
        }

        public GetWeatherReturnType.GetWeatherReturnTypeBuilderBase withTemperature(Double temperature) {
            ((GetWeatherReturnType) this.instance).temperature = temperature;
            return this;
        }

        public GetWeatherReturnType.GetWeatherReturnTypeBuilderBase withTemperatureUnit(GetWeatherReturnType.TemperatureUnit temperatureUnit) {
            ((GetWeatherReturnType) this.instance).temperatureUnit = temperatureUnit;
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
        private final static Map<String, GetWeatherReturnType.TemperatureUnit> CONSTANTS = new HashMap<String, GetWeatherReturnType.TemperatureUnit>();

        static {
            for (GetWeatherReturnType.TemperatureUnit c: values()) {
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

        public static GetWeatherReturnType.TemperatureUnit fromValue(String value) {
            GetWeatherReturnType.TemperatureUnit constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
