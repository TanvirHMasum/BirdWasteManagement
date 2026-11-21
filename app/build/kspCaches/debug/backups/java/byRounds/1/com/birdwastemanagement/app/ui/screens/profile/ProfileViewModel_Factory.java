package com.birdwastemanagement.app.ui.screens.profile;

import com.birdwastemanagement.app.data.local.preferences.UserPreferences;
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
public final class ProfileViewModel_Factory implements Factory<ProfileViewModel> {
  private final Provider<UserPreferences> userPreferencesProvider;

  public ProfileViewModel_Factory(Provider<UserPreferences> userPreferencesProvider) {
    this.userPreferencesProvider = userPreferencesProvider;
  }

  @Override
  public ProfileViewModel get() {
    return newInstance(userPreferencesProvider.get());
  }

  public static ProfileViewModel_Factory create(Provider<UserPreferences> userPreferencesProvider) {
    return new ProfileViewModel_Factory(userPreferencesProvider);
  }

  public static ProfileViewModel newInstance(UserPreferences userPreferences) {
    return new ProfileViewModel(userPreferences);
  }
}
