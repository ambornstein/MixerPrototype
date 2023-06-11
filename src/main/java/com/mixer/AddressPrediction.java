package com.mixer;

import com.google.maps.model.AutocompletePrediction;

public class AddressPrediction
{

    private final AutocompletePrediction prediction;

    public AddressPrediction(AutocompletePrediction prediction)
    {
        this.prediction = prediction;
    }

    @Override
    public String toString()
    {
        return prediction.description;
    }

    public AutocompletePrediction getPrediction()
    {
        return this.prediction;
    }

}
