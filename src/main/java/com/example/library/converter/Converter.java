package com.example.library.converter;

public interface Converter <FROM, TO>{

    TO convertFromSourceToModel (FROM resource);
    FROM convertFromModelToSource (TO model);
}
