
package com.javaaidev.easyllmtools.tools.mytool.model;


public class GetWeatherParameters {

    /**
     * Location
     * 
     */
    private String location;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetWeatherParameters() {
    }

    /**
     * 
     * @param location
     *     Location.
     */
    public GetWeatherParameters(String location) {
        super();
        this.location = location;
    }

    public static GetWeatherParameters.GetWeatherParametersBuilderBase builder() {
        return new GetWeatherParameters.GetWeatherParametersBuilder();
    }

    /**
     * Location
     * 
     */
    public String getLocation() {
        return location;
    }

    /**
     * Location
     * 
     */
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(GetWeatherParameters.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("location");
        sb.append('=');
        sb.append(((this.location == null)?"<null>":this.location));
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
        result = ((result* 31)+((this.location == null)? 0 :this.location.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GetWeatherParameters) == false) {
            return false;
        }
        GetWeatherParameters rhs = ((GetWeatherParameters) other);
        return ((this.location == rhs.location)||((this.location!= null)&&this.location.equals(rhs.location)));
    }

    public static class GetWeatherParametersBuilder
        extends GetWeatherParameters.GetWeatherParametersBuilderBase<GetWeatherParameters>
    {


        public GetWeatherParametersBuilder() {
            super();
        }

        public GetWeatherParametersBuilder(String location) {
            super(location);
        }

    }

    public static abstract class GetWeatherParametersBuilderBase<T extends GetWeatherParameters >{

        protected T instance;

        @SuppressWarnings("unchecked")
        public GetWeatherParametersBuilderBase() {
            // Skip initialization when called from subclass
            if (this.getClass().equals(GetWeatherParameters.GetWeatherParametersBuilder.class)) {
                this.instance = ((T) new GetWeatherParameters());
            }
        }

        @SuppressWarnings("unchecked")
        public GetWeatherParametersBuilderBase(String location) {
            // Skip initialization when called from subclass
            if (this.getClass().equals(GetWeatherParameters.GetWeatherParametersBuilder.class)) {
                this.instance = ((T) new GetWeatherParameters(location));
            }
        }

        public T build() {
            T result;
            result = this.instance;
            this.instance = null;
            return result;
        }

        public GetWeatherParameters.GetWeatherParametersBuilderBase withLocation(String location) {
            ((GetWeatherParameters) this.instance).location = location;
            return this;
        }

    }

}
