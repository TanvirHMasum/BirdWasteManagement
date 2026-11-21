package com.birdwastemanagement.app.ui.screens.details;

import com.birdwastemanagement.app.domain.repository.WasteRepository;
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
public final class CustomerDetailsViewModel_Factory implements Factory<CustomerDetailsViewModel> {
  private final Provider<WasteRepository> repositoryProvider;

  public CustomerDetailsViewModel_Factory(Provider<WasteRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public CustomerDetailsViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static CustomerDetailsViewModel_Factory create(
      Provider<WasteRepository> repositoryProvider) {
    return new CustomerDetailsViewModel_Factory(repositoryProvider);
  }

  public static CustomerDetailsViewModel newInstance(WasteRepository repository) {
    return new CustomerDetailsViewModel(repository);
  }
}
