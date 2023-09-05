plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:31.1-jre")

    // Add JSON-P (javax.json) dependencies
    implementation("javax.json:javax.json-api:1.1.4")
    implementation("org.glassfish:javax.json:1.1.4")
    implementation("org.json:json:20210307")

    implementation("org.apache.httpcomponents.client5:httpclient5:5.2.1")
}


application {
    // Define the main class for the application.
    mainClass.set("gradle_api_test.App")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
