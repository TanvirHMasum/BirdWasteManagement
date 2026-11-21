package com.birdwastemanagement.app.ui.screens.registration;

import com.birdwastemanagement.app.data.remote.api.AuthApi;
import com.birdwastemanagement.app.data.remote.api.LocationApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class RegistrationViewModel_Factory implements Factory<RegistrationViewModel> {
  private final Provider<LocationApi> locationApiProvider;

  private final Provider<AuthApi> authApiProvider;

  public RegistrationViewModel_Factory(Provider<LocationApi> locationApiProvider,
      Provider<AuthApi> authApiProvider) {
    this.locationApiProvider = locationApiProvider;
    this.authApiProvider = authApiProvider;
  }

  @Override
  public RegistrationViewModel get() {
    return newInstance(locationApiProvider.get(), authApiProvider.get());
  }

  public static RegistrationViewModel_Factory create(Provider<LocationApi> locationApiProvider,
      Provider<AuthApi> authApiProvider) {
    return new RegistrationViewModel_Factory(locationApiProvider, authApiProvider);
  }

  public static RegistrationViewModel newInstance(LocationApi locationApi, AuthApi authApi) {
    return new RegistrationViewModel(locationApi, authApi);
  }
}
