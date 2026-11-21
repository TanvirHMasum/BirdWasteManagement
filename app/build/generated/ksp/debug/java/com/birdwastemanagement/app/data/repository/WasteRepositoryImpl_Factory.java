package com.birdwastemanagement.app.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class WasteRepositoryImpl_Factory implements Factory<WasteRepositoryImpl> {
  @Override
  public WasteRepositoryImpl get() {
    return newInstance();
  }

  public static WasteRepositoryImpl_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static WasteRepositoryImpl newInstance() {
    return new WasteRepositoryImpl();
  }

  private static final class InstanceHolder {
    private static final WasteRepositoryImpl_Factory INSTANCE = new WasteRepositoryImpl_Factory();
  }
}
