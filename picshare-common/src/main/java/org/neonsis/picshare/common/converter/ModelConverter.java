package org.neonsis.picshare.common.converter;

import java.util.List;

public interface ModelConverter {

    <S,D> D convert(S source, Class<D> destinationClass);

    <S,D> List<D> convertList(List<S> source, Class<D> destinationClass);
}
