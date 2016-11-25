package de.ayesolutions.gogs.client.service;

import de.ayesolutions.gogs.client.GogsClient;
import de.ayesolutions.gogs.client.model.Comment;
import de.ayesolutions.gogs.client.model.Issue;
import de.ayesolutions.gogs.client.model.IssueLabel;
import de.ayesolutions.gogs.client.model.Milestone;

import java.util.List;

/**
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public class IssueService extends BaseService {

    public IssueService(GogsClient client) {
        super(client);
    }

    /**
     * GET /api/v1/repos/:username/:reponame/issues
     * Response
     *
     * @param username
     * @param repositoryName
     * @return
     */
    public List<Issue> listIssues(String username, String repositoryName) {
        return null;
    }

    /**
     * POST /api/v1/repos/:username/:reponame/issues
     * Response
     *
     * @param username
     * @param repositoryName
     * @param issue
     * @return
     */
    public Issue createIssues(String username, String repositoryName, Issue issue) {
        return null;
    }

    /**
     * GET /api/v1/repos/:username/:reponame/issues/:issueid
     * Response
     *
     * @param username
     * @param repositoryName
     * @param issueId
     * @return
     */
    public Issue getIssue(String username, String repositoryName, String issueId) {
        return null;
    }

    /**
     * PATCH /api/v1/repos/:username/:reponame/issues/:issueId
     * Response
     *
     * @param username
     * @param repositoryName
     * @param issue
     * @return
     */
    public Issue updateIssue(String username, String repositoryName, Issue issue) {
        return null;
    }

    /**
     * GET /api/v1/repos/:username/:reponame/comments/:commentId
     * Response
     *
     * @param username
     * @param repositoryName
     * @param issueId
     * @return
     */
    public List<Comment> listComments(String username, String repositoryName, String issueId) {
        return null;
    }

    /**
     * POST /api/v1/repos/:username/:reponame/comments
     * Response
     *
     * @param username
     * @param repositoryName
     * @param issueId
     * @param comment
     * @return
     */
    public Comment createComment(String username, String repositoryName, String issueId, Comment comment) {
        return null;
    }

    /**
     * PATCH /api/v1/repos/:username/:reponame/comments/:commentId
     * Response
     *
     * @param username
     * @param repositoryName
     * @param issueId
     * @param comment
     * @return
     */
    public Comment updateComment(String username, String repositoryName, String issueId, Comment comment) {
        return null;
    }

    /**
     * GET /api/v1/repos/:username/:reponame/issues/:issueId/labels
     * Response
     *
     * @param username
     * @param repositoryName
     * @param issueId
     * @return
     */
    public List<IssueLabel> listIssueLabels(String username, String repositoryName, String issueId) {
        return null;
    }

    /**
     * POST /api/v1/repos/:username/:reponame/issues/:issueId/labels
     * Response
     *
     * @param username
     * @param repositoryName
     * @param issueId
     * @param issueLabelList
     * @return
     */
    public List<IssueLabel> createIssueLabels(String username, String repositoryName, String issueId, List<IssueLabel> issueLabelList) {
        return null;
    }

    /**
     * PUT /api/v1/repos/:username/:reponame/issues/:issueId/labels
     * Response
     *
     * @param username
     * @param repositoryName
     * @param issueId
     * @param issueLabelList
     * @return
     */
    public List<IssueLabel> replaceIssueLabels(String username, String repositoryName, String issueId, List<IssueLabel> issueLabelList) {
        return null;
    }

    /**
     * DELETE /api/v1/repos/:username/:reponame/issues/:issueId/labels
     * Response
     *
     * @param username
     * @param repositoryName
     * @param issueId
     * @return
     */
    public Boolean deleteIssueLabels(String username, String repositoryName, String issueId) {
        return null;
    }

    /**
     * GET /api/v1/repos/:username/:reponame/labels
     * Response
     *
     * @param username
     * @param repositoryName
     * @return
     */
    public List<IssueLabel> getIssueLabels(String username, String repositoryName) {
        return null;
    }

    /**
     * POST /api/v1/repos/:username/:reponame/labels
     * Response
     *
     * @param username
     * @param repositoryName
     * @param issueLabel
     * @return
     */
    public IssueLabel addIssueLabel(String username, String repositoryName, IssueLabel issueLabel) {
        return null;
    }

    /**
     * PATCH /api/v1/repos/:username/:reponame/labels/:labelId
     * Response
     *
     * @param username
     * @param repositoryName
     * @param issueLabel
     * @return
     */
    public IssueLabel updateIssueLabel(String username, String repositoryName, IssueLabel issueLabel) {
        return null;
    }

    /**
     * GET /api/v1/repos/:username/:reponame/labels/:labelId
     * Response
     *
     * @param username
     * @param repositoryName
     * @param issueLabelId
     * @return
     */
    public IssueLabel getIssueLabel(String username, String repositoryName, String issueLabelId) {
        return null;
    }

    /**
     * DELETE /api/v1/repos/:username/:reponame/labels/:labelId
     * Response
     *
     * @param username
     * @param repositoryName
     * @param issueLabelId
     * @return
     */
    public Boolean deleteIssueLabel(String username, String repositoryName, String issueLabelId) {
        return null;
    }

    /**
     * GET /api/v1/repos/:username/:reponame/milestones
     * Response
     *
     * @param username
     * @param repositoryName
     * @return
     */
    public List<Milestone> getMilestones(String username, String repositoryName) {
        return null;
    }

    /**
     * POST /api/v1/repos/:username/:reponame/milestones
     * Response
     *
     * @param username
     * @param repositoryName
     * @param milestone
     * @return
     */
    public Milestone addMilestone(String username, String repositoryName, Milestone milestone) {
        return null;
    }

    /**
     * PATCH /api/v1/repos/:username/:reponame/milestones/:milestoneId
     * Response
     *
     * @param username
     * @param repositoryName
     * @param milestone
     * @return
     */
    public Milestone updateMilestone(String username, String repositoryName, Milestone milestone) {
        return null;
    }

    /**
     * GET /api/v1/repos/:username/:reponame/milestones/:milestoneId
     * Response
     *
     * @param username
     * @param repositoryName
     * @param milestoneId
     * @return
     */
    public Milestone getMilestone(String username, String repositoryName, String milestoneId) {
        return null;
    }

    /**
     * DELETE /api/v1/repos/:username/:reponame/milestones/:milestoneId
     * Response
     *
     * @param username
     * @param repositoryName
     * @param milestoneId
     * @return
     */
    public Boolean deleteMilestone(String username, String repositoryName, String milestoneId) {
        return null;
    }
}
