plugins {
    java
    `maven-publish`
}

group = "me.adipol.modernjda"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-alpha.5")
    implementation("com.google.code.gson:gson:2.9.0")

    annotationProcessor("org.projectlombok:lombok:1.18.22")
    compileOnly("org.projectlombok:lombok:1.18.22")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

publishing {
    repositories {
        maven {
            name = "local-repository"
            url = uri("https://repo.projectcode.pl/releases")

            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}