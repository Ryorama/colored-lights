package dev.gegy.colored_lights.mixin.resource;

import dev.gegy.colored_lights.resource.ResourcePatchManager;
import net.minecraft.client.resource.ResourceIndex;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.metadata.ResourceMetadata;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.io.InputStream;

@Mixin(Resource.class)
public class ResourceMixin {
    @Shadow @Final private Resource.InputSupplier<InputStream> inputSupplier;
    @Shadow @Final private String resourcePackName;

    @Unique
    private boolean colored_lights$patchedResource;

    @Inject(method = "getInputStream", at = @At("HEAD"))
    private void getInputStream(CallbackInfoReturnable<InputStream> ci) throws IOException {
        if (!this.colored_lights$patchedResource) {
            this.colored_lights$patchedResource = true;
            InputStream inputStream = this.inputSupplier.get();
            inputStream = ResourcePatchManager.INSTANCE.patch(new Identifier(Identifier.DEFAULT_NAMESPACE, resourcePackName), this.inputSupplier.get());
        }
    }
}
