package com.vherasymenko.avro.schemaLoader

/**
 * Contains the information about the schemas we want to register in the schema register when application starts.
 */
enum SchemasNamespaceRegistry {

    COURSE_INSTALL( 'event.course_install', 'CourseInstall' ),
    LESSON_STATUS( 'event.lesson_status', 'LessonStatus' ),
    USER_CREATE( 'event.user_create', 'UserCreate' ),

    final String namespace

    final String className

    final String fullNamespace

    SchemasNamespaceRegistry( String aNamespace, String aClassName ) {
        namespace = aNamespace
        className = aClassName
        fullNamespace = namespace + '.' + className
    }

}