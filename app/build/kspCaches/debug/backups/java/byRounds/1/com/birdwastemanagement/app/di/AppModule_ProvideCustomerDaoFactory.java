package com.birdwastemanagement.app.di;

import com.birdwastemanagement.app.data.local.AppDatabase;
import com.birdwastemanagement.app.data.local.CustomerDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideCustomerDaoFactory implements Factory<CustomerDao> {
  private final Provider<AppDatabase> databaseProvider;

  public AppModule_ProvideCustomerDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public CustomerDao get() {
    return provideCustomerDao(databaseProvider.get());
  }

  public static AppModule_ProvideCustomerDaoFactory create(Provider<AppDatabase> databaseProvider) {
    return new AppModule_ProvideCustomerDaoFactory(databaseProvider);
  }

  public static CustomerDao provideCustomerDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideCustomerDao(database));
  }
}
