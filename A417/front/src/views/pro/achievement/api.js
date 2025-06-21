import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getAchievementOne = (params) => {
    return getRequest('/achievement/getOne', params)
}
export const getAchievementList = (params) => {
    return getRequest('/achievement/getByPage', params)
}
export const getAchievementCount = (params) => {
    return getRequest('/achievement/count', params)
}
export const addAchievement = (params) => {
    return postRequest('/achievement/insert', params)
}
export const editAchievement = (params) => {
    return postRequest('/achievement/update', params)
}
export const addOrEditAchievement = (params) => {
    return postRequest('/achievement/insertOrUpdate', params)
}
export const deleteAchievement = (params) => {
    return postRequest('/achievement/delByIds', params)
}
export const getCurriculumList = (params) => {
    return getRequest('/curriculum/getAll', params)
}
export const getUserList = (params) => {
    return getRequest('/superUser/getUserList', params)
}