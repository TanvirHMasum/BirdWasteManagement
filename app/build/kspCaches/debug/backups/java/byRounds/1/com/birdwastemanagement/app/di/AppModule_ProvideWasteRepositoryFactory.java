package com.birdwastemanagement.app.di;

import com.birdwastemanagement.app.data.repository.WasteRepositoryImpl;
import com.birdwastemanagement.app.domain.repository.WasteRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class AppModule_ProvideWasteRepositoryFactory implements Factory<WasteRepository> {
  private final Provider<WasteRepositoryImpl> repositoryImplProvider;

  public AppModule_ProvideWasteRepositoryFactory(
      Provider<WasteRepositoryImpl> repositoryImplProvider) {
    this.repositoryImplProvider = repositoryImplProvider;
  }

  @Override
  public WasteRepository get() {
    return provideWasteRepository(repositoryImplProvider.get());
  }

  public static AppModule_ProvideWasteRepositoryFactory create(
      Provider<WasteRepositoryImpl> repositoryImplProvider) {
    return new AppModule_ProvideWasteRepositoryFactory(repositoryImplProvider);
  }

  public static WasteRepository provideWasteRepository(WasteRepositoryImpl repositoryImpl) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideWasteRepository(repositoryImpl));
  }
}
