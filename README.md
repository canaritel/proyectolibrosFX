# proyectolibrosFX

Para ejecutar el proyecto deben incluir 2 carpetas, dentro de la carpeta Proyecto tienen dichas carpetas (bin y lib ,  bd_portable es para la BD tipo fichero).
El archivo .zip contiene todas las carpetas y ficheros para lanzar la aplicación en Windows.  
Se debe lanzar el fichero ejecuta.bat en Windows, para Linux y macOS se debe crear un fichero .sh

Fichero .bat Windows:

    java --module-path "./lib" --add-modules javafx.controls,javafx.fxml -jar appFX.jar
    
Fichero .sh para Linux:

    #!/bin/bash

    # -*- ENCODING: UTF-8 -*-
    
    java --module-path "./lib" --add-modules javafx.controls,javafx.fxml -jar appFX.jar

Tienen más información en mi canal de Youtube, donde se explica el proyecto, librerías y funciones.
https://youtu.be/6EQGm4BxK2o

Las versiones de Java:
- Java 11 LTS (JDK y JRE) https://adoptopenjdk.net/
- JavaFX 15 (solo SDK) https://openjfx.io/

Si desea ejecutar la aplicación en distintos S.O. debes realizar los siguientes pasos:
- Descargar el JavaFX SDK versión 15 del S.O. que desees
- Reemplazar la carpeta BIN de la carpeta Proyecto por la nueva 

Para la compilación es necesario insertar estas instrucciones en el BUILD:

    <project name="Pruebas_Login_JavaFX" default="default" basedir=".">
    <description>Builds, tests, and runs the project Pruebas_Login_JavaFX.</description>
    <import file="nbproject/build-impl.xml"/>
    <target name="-post-jar">
    <property name="store.jar.name" value="appFX"/>  <!-- Puedes cambiar el nombre de la aplicación compilada "appFX" -->
    <property name="store.dir" value="Proyecto"/>    <!-- Puedes cambiar el nombre de la carpeta donde se compila el proyecto "Proyecto" -->
    <property name="store.jar" value="${store.dir}/${store.jar.name}.jar"/>
    <echo message="Packaging ${application.title} into a single JAR at ${store.jar}"/>
    <jar destfile="${store.dir}/temp_final.jar" filesetmanifest="skip">
        <zipgroupfileset dir="dist" includes="*.jar"/>
        <zipgroupfileset dir="dist/lib" includes="*.jar"/>
 
    <manifest>
            <attribute name="Main-Class" value="${main.class}"/>
        </manifest>
    </jar>
 
    <zip destfile="${store.jar}">
        <zipfileset src="${store.dir}/temp_final.jar"
        excludes="META-INF/*.SF, META-INF/*.DSA, META-INF/*.RSA"/>
    </zip>
 
     <delete file="${store.dir}/temp_final.jar"/>
    <delete dir="${store.dir}/lib"/>
    <delete file="${store.dir}/README.TXT"/>
    </target>
    
    </project>

