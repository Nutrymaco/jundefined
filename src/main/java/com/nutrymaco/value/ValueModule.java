package com.nutrymaco.value;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.Module;


class ValueModule extends Module {

    @Override
    public String getModuleName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }

    @Override
    public void setupModule(SetupContext setupContext) {
        setupContext.addDeserializers(new ValueDeserializers());
        setupContext.addSerializers(new ValueSerializers());
        setupContext.addTypeModifier(new ValueTypeModifier());
    }
}
