package com.birdwastemanagement.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.birdwastemanagement.app.ui.screens.details.CustomerDetailsScreen
import com.birdwastemanagement.app.ui.screens.home.DashboardScreen
import com.birdwastemanagement.app.ui.screens.login.LoginScreen
import com.birdwastemanagement.app.ui.screens.registration.RegistrationScreen
import com.birdwastemanagement.app.ui.screens.otp.OtpScreen
import com.birdwastemanagement.app.ui.screens.payment.BillPaymentScreen
import com.birdwastemanagement.app.ui.screens.profile.ProfileScreen
import com.birdwastemanagement.app.ui.screens.receipt.PaymentReceiptScreen
import com.birdwastemanagement.app.ui.screens.splash.SplashScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Registration : Screen("registration")
    object Otp : Screen("otp/{phone}") {
        fun createRoute(phone: String) = "otp/$phone"
    }
    object Dashboard : Screen("dashboard")
    object CustomerDetails : Screen("customer_details/{customerId}") {
        fun createRoute(customerId: String) = "customer_details/$customerId"
    }
    object BillPayment : Screen("bill_payment/{customerId}") {
        fun createRoute(customerId: String) = "bill_payment/$customerId"
    }
    object PaymentReceipt : Screen("payment_receipt/{receiptNo}/{amount}/{paymentMethod}") {
        fun createRoute(receiptNo: String, amount: String, paymentMethod: String) =
            "payment_receipt/$receiptNo/$amount/$paymentMethod"
    }
    object Profile : Screen("profile")
}

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegistration = {
                    navController.navigate(Screen.Registration.route)
                }
            )
        }

        composable(Screen.Registration.route) {
            RegistrationScreen(
                onBackClick = { navController.popBackStack() },
                onNavigateToOtp = { phone ->
                    navController.navigate(Screen.Otp.createRoute(phone))
                }
            )
        }

        composable(
            route = Screen.Otp.route,
            arguments = listOf(navArgument("phone") { type = NavType.StringType })
        ) { backStackEntry ->
            val phone = backStackEntry.arguments?.getString("phone") ?: ""
            OtpScreen(
                phone = phone,
                onBackClick = { navController.popBackStack() },
                onVerifySuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onCustomerClick = { customerId ->
                    navController.navigate(Screen.CustomerDetails.createRoute(customerId))
                },
                onProfileClick = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }

        composable(
            route = Screen.CustomerDetails.route,
            arguments = listOf(navArgument("customerId") { type = NavType.StringType })
        ) { backStackEntry ->
            val customerId = backStackEntry.arguments?.getString("customerId") ?: "CUST-001"
            CustomerDetailsScreen(
                customerId = customerId,
                onBackClick = { navController.popBackStack() },
                onCollectPaymentClick = {
                    navController.navigate(Screen.BillPayment.createRoute(customerId))
                }
            )
        }

        composable(
            route = Screen.BillPayment.route,
            arguments = listOf(navArgument("customerId") { type = NavType.StringType })
        ) { backStackEntry ->
            val customerId = backStackEntry.arguments?.getString("customerId") ?: "CUST-001"
            BillPaymentScreen(
                customerId = customerId,
                onBackClick = { navController.popBackStack() },
                onPaymentProcessed = { receiptNo, amount, method ->
                    navController.navigate(
                        Screen.PaymentReceipt.createRoute(receiptNo, amount.toString(), method)
                    ) {
                        popUpTo(Screen.Dashboard.route) { inclusive = false }
                    }
                }
            )
        }

        composable(
            route = Screen.PaymentReceipt.route,
            arguments = listOf(
                navArgument("receiptNo") { type = NavType.StringType },
                navArgument("amount") { type = NavType.StringType },
                navArgument("paymentMethod") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val receiptNo = backStackEntry.arguments?.getString("receiptNo") ?: "REC-001"
            val amount = backStackEntry.arguments?.getString("amount") ?: "200.0"
            val method = backStackEntry.arguments?.getString("paymentMethod") ?: "Cash"

            PaymentReceiptScreen(
                receiptNo = receiptNo,
                amount = amount,
                paymentMethod = method,
                onDoneClick = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                onBackClick = { navController.popBackStack() },
                onLogoutClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
