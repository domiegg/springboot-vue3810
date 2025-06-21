import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getCurriculumOne = (params) => {
    return getRequest('/curriculum/getOne', params)
}
export const getCurriculumList = (params) => {
    return getRequest('/curriculum/getByPage', params)
}
export const getCurriculumCount = (params) => {
    return getRequest('/curriculum/count', params)
}
export const addCurriculum = (params) => {
    return postRequest('/curriculum/insert', params)
}
export const editCurriculum = (params) => {
    return postRequest('/curriculum/update', params)
}
export const addOrEditCurriculum = (params) => {
    return postRequest('/curriculum/insertOrUpdate', params)
}
export const deleteCurriculum = (params) => {
    return postRequest('/curriculum/delByIds', params)
}