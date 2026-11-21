package com.birdwastemanagement.app.ui.screens.login;

import com.birdwastemanagement.app.data.local.preferences.UserPreferences;
import com.birdwastemanagement.app.data.remote.api.AuthApi;
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
public final class LoginViewModel_Factory implements Factory<LoginViewModel> {
  private final Provider<UserPreferences> userPreferencesProvider;

  private final Provider<AuthApi> authApiProvider;

  public LoginViewModel_Factory(Provider<UserPreferences> userPreferencesProvider,
      Provider<AuthApi> authApiProvider) {
    this.userPreferencesProvider = userPreferencesProvider;
    this.authApiProvider = authApiProvider;
  }

  @Override
  public LoginViewModel get() {
    return newInstance(userPreferencesProvider.get(), authApiProvider.get());
  }

  public static LoginViewModel_Factory create(Provider<UserPreferences> userPreferencesProvider,
      Provider<AuthApi> authApiProvider) {
    return new LoginViewModel_Factory(userPreferencesProvider, authApiProvider);
  }

  public static LoginViewModel newInstance(UserPreferences userPreferences, AuthApi authApi) {
    return new LoginViewModel(userPreferences, authApi);
  }
}
