package com.mpautasso.sistemavtv.mapper;

import com.mpautasso.sistemavtv.model.Inspector;
import com.mpautasso.sistemavtv.model.dtos.inspector.InspectorRequest;
import com.mpautasso.sistemavtv.model.dtos.inspector.InspectorResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InspectorMapper {
    private final ModelMapper modelMapper;

    public Inspector fromRequestToEntity(InspectorRequest inspectorRequest){
        return modelMapper.map(inspectorRequest, Inspector.class);
    }

    public InspectorResponse fromEntityToResponse(Inspector inspector){
        return modelMapper.map(inspector, InspectorResponse.class);
    }
}
