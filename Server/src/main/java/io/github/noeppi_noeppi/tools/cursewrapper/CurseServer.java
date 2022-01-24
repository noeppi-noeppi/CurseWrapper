package io.github.noeppi_noeppi.tools.cursewrapper;

import io.github.noeppi_noeppi.tools.cursewrapper.backend.CurseApi;
import io.github.noeppi_noeppi.tools.cursewrapper.cache.CurseCache;
import io.github.noeppi_noeppi.tools.cursewrapper.route.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Service;

import java.nio.file.Path;

public class CurseServer {
    
    private static final Logger logger = LoggerFactory.getLogger(CurseServer.class);
    
    private final Service spark;
    
    public CurseServer(int port, SslData ssl, int threads, CurseApi api, CurseCache cache) {
        logger.info("Starting Server on port {}.", port);
        this.spark = Service.ignite();
        this.spark.port(port);
        this.spark.threadPool(threads, threads, -1);
        if (ssl != null) {
            this.spark.secure(ssl.cert().toAbsolutePath().normalize().toString(), ssl.key(), null, null);
        } else {
            logger.warn("Running without SSL.");
        }
        
        this.spark.get("/search", new SearchRoute(this.spark, api, cache));
        this.spark.get("/slug/:projectId", new SlugRoute(this.spark, api, cache));
        this.spark.get("/project/:projectId", new ProjectRoute(this.spark, api, cache));
        this.spark.get("/project/:projectId/files", new FilesRoute(this.spark, api, cache));
        this.spark.get("/project/:projectId/file/:fileId", new FileRoute(this.spark, api, cache));
        this.spark.get("/project/:projectId/changelog/:fileId", new ChangelogRoute(this.spark, api, cache));
        
        this.spark.awaitInitialization();
    }
    
    public void shutdown() {
        this.spark.stop();
    }
    
    public record SslData(String key, Path cert) {}
}
