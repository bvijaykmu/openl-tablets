package org.openl.rules.ui.repository;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.openl.rules.repository.CommonVersionImpl;
import org.openl.rules.webstudio.RulesUserSession;
import org.openl.rules.webstudio.util.FacesUtils;
import org.openl.rules.workspace.abstracts.ProjectException;
import org.openl.rules.workspace.abstracts.ProjectVersion;
import org.openl.rules.workspace.uw.UserWorkspace;
import org.openl.rules.workspace.uw.UserWorkspaceDeploymentProject;
import org.openl.rules.workspace.uw.UserWorkspaceProject;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.model.SelectItem;


/**
 * Deployment controller.
 *
 * @author Andrey Naumenko
 */
public class DeploymentController implements Serializable {
    private static final long serialVersionUID = 1L;
    private final static Log log = LogFactory.getLog(DeploymentController.class);
    private List<DeploymentDescriptorItem> items;
    private String projectName;
    private String version;
    private SelectItem[] projects;

    public DeploymentController() {
        items = new ArrayList<DeploymentDescriptorItem>();
        items.add(new DeploymentDescriptorItem("Project 1", "1.2.1"));
        items.add(new DeploymentDescriptorItem("Project 2", "1.2.2",
                "Conflicts with project 5 v1.0.4"));
        items.add(new DeploymentDescriptorItem("Project 5", "1.0.4"));
    }

    public List<DeploymentDescriptorItem> getItems() {
        return items;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String addItem() {
        DeploymentDescriptorItem newItem = new DeploymentDescriptorItem(projectName,
                version);
        if (!items.contains(newItem)) {
            items.add(newItem);
        }
        return null;
    }

    public String deleteItem() {
        Integer key = Integer.valueOf(FacesUtils.getRequestParameter("key"));
        items.remove(key.intValue());
        return null;
    }

    public String save() {
        UserWorkspace workspace = getWorkspace();

        String name = "123";

        try {
            workspace.createDDProject(name);

            UserWorkspaceDeploymentProject ddp = workspace.getDDProject(name);
            ddp.checkOut();

            for (DeploymentDescriptorItem item : items) {
                String[] version = StringUtils.split(item.getVersion(), '.');
                int major = 0;
                int minor = 0;
                int revision = 0;
                if (version.length > 0) {
                    major = Integer.parseInt(version[0]);
                }
                if (version.length > 1) {
                    minor = Integer.parseInt(version[1]);
                }
                if (version.length > 2) {
                    revision = Integer.parseInt(version[2]);
                }

                ddp.addProjectDescriptor(item.getName(),
                    new CommonVersionImpl(major, minor, revision));
            }

            ddp.checkIn();
        } catch (Exception e) {
            log.error("Cannot create new DDP " + name);
            return null;
        }

        return null;
    }

    private UserWorkspace getWorkspace() {
        try {
            RulesUserSession rulesUserSession = (RulesUserSession) FacesUtils.getSessionMap()
                    .get("rulesUserSession");

            UserWorkspace workspace = null;
            workspace = rulesUserSession.getUserWorkspace();
            return workspace;
        } catch (Exception e) {
            log.error("Error obtaining user workspace", e);
        }
        return null;
    }

    public SelectItem[] getProjects() {
        if (projects == null) {
            UserWorkspace workspace = getWorkspace();
            Collection<UserWorkspaceProject> workspaceProjects = workspace.getProjects();
            List<SelectItem> selectItems = new ArrayList<SelectItem>();
            for (UserWorkspaceProject project : workspaceProjects) {
                selectItems.add(new SelectItem(project.getName()));
                if (projectName == null) {
                    projectName = project.getName();
                }
            }
            projects = selectItems.toArray(new SelectItem[0]);
        }

        return projects;
    }

    public SelectItem[] getProjectVersions() {
        UserWorkspace workspace = getWorkspace();
        if (projectName == null) {
            return new SelectItem[0];
        }

        try {
            UserWorkspaceProject project = workspace.getProject(projectName);

            List<SelectItem> selectItems = new ArrayList<SelectItem>();
            for (ProjectVersion version : project.getVersions()) {
                selectItems.add(new SelectItem(version.getVersionName()));
            }
            return selectItems.toArray(new SelectItem[0]);
        } catch (ProjectException e) {
            log.error(e);
        }

        return new SelectItem[0];
    }

    public String openSelectedProjects() {
        UserWorkspace workspace = getWorkspace();
        for (DeploymentDescriptorItem item : items) {
            if (item.isSelected()) {
                String projectName = item.getName();
                UserWorkspaceProject project;
                try {
                    project = workspace.getProject(projectName);
                    if (!project.isOpened()) {
                        project.open();
                    }
                } catch (ProjectException e) {
                    log.error("Error obtaining project " + projectName, e);
                }
            }
        }

        return null;
    }

    public String deploy() {
        return null;
    }

    public String checkIn() {
        return null;
    }

    public String checkOut() {
        return null;
    }

    public boolean isCheckinable() {
        return true;
    }

    public boolean isCheckoutable() {
        return true;
    }
}
