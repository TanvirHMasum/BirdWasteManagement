package com.birdwastemanagement.app.ui.screens.payment;

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
public final class PaymentViewModel_Factory implements Factory<PaymentViewModel> {
  private final Provider<WasteRepository> repositoryProvider;

  public PaymentViewModel_Factory(Provider<WasteRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public PaymentViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static PaymentViewModel_Factory create(Provider<WasteRepository> repositoryProvider) {
    return new PaymentViewModel_Factory(repositoryProvider);
  }

  public static PaymentViewModel newInstance(WasteRepository repository) {
    return new PaymentViewModel(repository);
  }
}
